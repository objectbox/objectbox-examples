#!/usr/bin/env bash
set -euo pipefail

# This script will replace the ObjectBox version strings in:
# - gradle/libs.versions.toml
# - java-main-maven/pom.xml
# - java-main-maven/README.md
# And commit the changes.

if [[ $# -lt 1 ]]; then
    echo "Usage: $0 <versionNew>"
    exit 1
fi
versionNew="$1"

# macOS includes the BSD version of sed, which has different options and syntax than the
# expected GNU version. So require users of this script to install gsed.
if [[ "$OSTYPE" == "darwin"* ]]; then
  if ! command -v gsed &>/dev/null; then
    echo "Error: gsed is required but not installed. Install it, for example using 'brew install gsed'."
    exit 1
  fi
  sed="gsed"
else
  sed="sed"
fi

# Replace versions
scriptDir="$(dirname "$0")"
rootProjectDir="$scriptDir/.."

gradleVersionCatalog="$rootProjectDir/gradle/libs.versions.toml"
$sed --in-place "s/objectbox = \".*\"/objectbox = \"$versionNew\"/g" "$gradleVersionCatalog"

mavenPom="$rootProjectDir/java-main-maven/pom.xml"
$sed --in-place "s/<objectboxVersion>.*<\/objectboxVersion>/<objectboxVersion>$versionNew<\/objectboxVersion>/g" "$mavenPom"

mavenReadme="$rootProjectDir/java-main-maven/README.md"
$sed --in-place "s/<objectboxVersion>.*<\/objectboxVersion>/<objectboxVersion>$versionNew<\/objectboxVersion>/g" "$mavenReadme"

git commit --all --message="Update ObjectBox to $versionNew"
