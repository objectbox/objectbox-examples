package io.objectbox.example.relation;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;
import io.objectbox.relation.ToMany;

import static io.objectbox.query.QueryBuilder.StringOrder.CASE_SENSITIVE;

/** ObjectBox relations example (https://docs.objectbox.io/relations) */
public class RelationActivity extends Activity {

    private TextView textViewLog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textViewLog = findViewById(R.id.textViewLog);

        long start = System.currentTimeMillis();
        ordersAndCustomers();
        studentsAndTeachers();
        long time = System.currentTimeMillis() - start;
        log("\nDone in " + time + "ms");
    }

    /**
     * Demonstrates a one-to-many (1:N) relation.
     * Order has a to-one to Customer.
     * Customer has a to-many backlink to Order.
     */
    private void ordersAndCustomers() {
        Box<Customer> customerBox = ObjectBox.get().boxFor(Customer.class);
        Box<Order> orderBox = ObjectBox.get().boxFor(Order.class);

        // Remove all previous object to have clear start for simplicity's sake
        customerBox.removeAll();
        orderBox.removeAll();

        logTitle("Add a customer with an order (using to-one)");
        Customer customer = new Customer();
        Order order1 = new Order();
        order1.customer.setTarget(customer);
        orderBox.put(order1);
        logOrders(orderBox, customer);

        logTitle("Add two orders to the customer (from the other side of the relation using the to-many backlink)");
        customer.orders.reset(); // just to be on the safe side before adding
        customer.orders.add(new Order());
        customer.orders.add(new Order());
        customerBox.put(customer);
        logOrders(orderBox, customer);

        logTitle("Remove (delete) the first order object");
        orderBox.remove(order1);
        logOrders(orderBox, customer);

        logTitle("Remove an order from the to-many relation (does not delete the order object)");
        customer.orders.reset();
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
        log("");
    }

    /**
     * Demonstrates a to-many relation.
     * Student has a to-many to Teacher.
     */
    private void studentsAndTeachers() {
        Box<Student> studentBox = ObjectBox.get().boxFor(Student.class);
        Box<Teacher> teacherBox = ObjectBox.get().boxFor(Teacher.class);

        // Remove all previous object to have clear start for simplicity's sake
        studentBox.removeAll();
        teacherBox.removeAll();

        logTitle("Add two students and two teachers; first student has two teachers, second student has one teacher");
        Teacher obiWan = new Teacher("Obi-Wan Kenobi");
        Teacher yoda = new Teacher("Yoda");
        Student luke = new Student("Luke Skywalker");
        luke.teachers.add(obiWan);
        luke.teachers.add(yoda);
        Student anakin = new Student("Anakin Skywalker");
        anakin.teachers.add(obiWan);
        studentBox.put(luke, anakin);
        logTeachers(studentBox, teacherBox);

        // https://docs.objectbox.io/queries
        logTitle("Query for all students named \"Skywalker\" taught by \"Yoda\"");
        QueryBuilder<Student> builder = studentBox.query().contains(Student_.name, "Skywalker", CASE_SENSITIVE);
        builder.link(Student_.teachers).equal(Teacher_.name, yoda.name, CASE_SENSITIVE);
        List<Student> studentsTaughtByYoda = builder.build().find();
        log("There is " + studentsTaughtByYoda.size() + " student taught by Yoda: "
                + studentsTaughtByYoda.get(0).name);
        log("");

        logTitle("Remove first teacher from first student");
        luke.teachers.remove(obiWan);
        luke.teachers.applyChangesToDb(); // more efficient than studentBox.put(student1);
        logTeachers(studentBox, teacherBox);

        logTitle("Remove student of second teacher using backlink");
        yoda.students.clear();
        yoda.students.applyChangesToDb();
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
        log("");
    }

    private void log(String message) {
        Log.d(App.TAG, message);
        textViewLog.append(message + "\n");
    }

    private void logTitle(String message) {
        Log.d(App.TAG, ">>> " + message + " <<<");
        Spannable spannableString = new SpannableString(message.concat("\n"));
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLog.append(spannableString);
    }

}