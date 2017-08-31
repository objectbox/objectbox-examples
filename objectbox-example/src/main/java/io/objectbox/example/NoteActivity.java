package io.objectbox.example;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.relation.ToMany;

public class NoteActivity extends Activity {

    private EditText editText;
    private View addNoteButton;
    private TextView textViewLog;

    private Box<Note> notesBox;
    private Query<Note> notesQuery;
    private NotesAdapter notesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setUpViews();

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        notesBox = boxStore.boxFor(Note.class);

        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        notesQuery = notesBox.query().order(Note_.text).build();
        updateNotes();

        // relations example (http://objectbox.io/documentation/relations/)
        ordersAndCustomers(boxStore);
        studentsAndTeachers(boxStore);
    }

    /** Manual trigger to re-query and update the UI. For a reactive alternative check {@link ReactiveNoteActivity}. */
    private void updateNotes() {
        List<Note> notes = notesQuery.find();
        notesAdapter.setNotes(notes);
    }

    protected void setUpViews() {
        ListView listView = findViewById(R.id.listViewNotes);
        listView.setOnItemClickListener(noteClickListener);

        notesAdapter = new NotesAdapter();
        listView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        addNoteButton.setEnabled(false);

        editText = findViewById(R.id.editTextNote);
        editText.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        textViewLog = findViewById(R.id.textViewLog);
    }

    public void onAddButtonClick(Button view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        notesBox.put(note);
        log("Inserted new note, ID: " + note.getId());

        updateNotes();
    }

    OnItemClickListener noteClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Note note = notesAdapter.getItem(position);
            notesBox.remove(note);
            log("Deleted note, ID: " + note.getId());

            updateNotes();
        }
    };

    private void ordersAndCustomers(BoxStore boxStore) {
        Box<Customer> customerBox = boxStore.boxFor(Customer.class);
        Box<Order> orderBox = boxStore.boxFor(Order.class);
        customerBox.removeAll();
        orderBox.removeAll();

        Customer customer = new Customer();

        // add using to-one
        Order order1 = new Order();
        order1.customer.setTarget(customer);
        orderBox.put(order1);

        logOrders(orderBox, customer);

        // add using to-many
        customer.orders.add(new Order());
        customer.orders.add(new Order());
        customerBox.put(customer);

        logOrders(orderBox, customer);

        // remove using to-one
        order1.customer.setTarget(null);
        orderBox.put(order1);

        // remove using to-many
        customer.orders.reset();
        customer.orders.remove(0);
        customer.orders.remove(0);
        customerBox.put(customer);

        logOrders(orderBox, customer);
    }

    private void logOrders(Box<Order> orderBox, Customer customer) {
        List<Order> ordersQueried = orderBox.query().equal(Order_.customerId, customer.id).build().find();
        log("Customer " + customer.id + " has " + ordersQueried.size() + " orders");
        for (Order order : ordersQueried) {
            log("Order " + order.id + " related to customer " + order.customer.getTargetId());
        }
    }

    private void studentsAndTeachers(BoxStore boxStore) {
        Box<Student> studentBox = boxStore.boxFor(Student.class);
        Box<Teacher> teacherBox = boxStore.boxFor(Teacher.class);
        studentBox.removeAll();
        teacherBox.removeAll();

        // add
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();

        Student student1 = new Student();
        student1.teachers.add(teacher1);
        student1.teachers.add(teacher2);

        Student student2 = new Student();
        student2.teachers.add(teacher1);

        studentBox.put(student1, student2);

        logTeachers(studentBox, teacherBox);

        // remove
        student1.teachers.remove(teacher2);
        studentBox.put(student1);

        logTeachers(studentBox, teacherBox);
    }

    private void logTeachers(Box<Student> studentBox, Box<Teacher> teacherBox) {
        log("There are " + teacherBox.count() + " teachers");
        List<Student> students = studentBox.getAll();
        for (Student student : students) {
            ToMany<Teacher> teachersToMany = student.teachers;
            for (Teacher teacher : teachersToMany) {
                log("Student " + student.id + " is taught by teacher " + teacher.id);
            }
        }
    }

    private void log(String message) {
        Log.d(App.TAG, message);
        message = message + "\n" + textViewLog.getText();
        textViewLog.setText(message);
    }
}