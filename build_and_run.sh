#!/bin/zsh

# Shell script for compiling and running the QuizApp application
# Created: September 26, 2025

# Define common variables with absolute paths
PROJECT_ROOT=$(cd "$(dirname "$0")" && pwd)
FRONTEND_SRC="$PROJECT_ROOT/Frontend/src"
APP_DIR="$FRONTEND_SRC/quiz/app"
LIB_DIR="$PROJECT_ROOT/lib"
GSON_JAR="$LIB_DIR/gson-2.13.2.jar"
MAIN_CLASS="quiz.app.Login"

# Colors for terminal output
GREEN="\033[0;32m"
RED="\033[0;31m"
YELLOW="\033[0;33m"
RESET="\033[0m"

# Print environment information
echo "${YELLOW}Build environment:${RESET}"
echo "Project root: $PROJECT_ROOT"
echo "Gson JAR path: $GSON_JAR"

# Function to compile the application
compile() {
    echo "${YELLOW}Compiling QuizApp...${RESET}"

    # First make sure we're in the right directory
    cd "$FRONTEND_SRC" || { echo "${RED}Failed to change directory to $FRONTEND_SRC${RESET}"; exit 1; }

    # Use absolute path for the Gson JAR file
    echo "Running compilation with explicit Gson JAR path"
    javac -cp "$GSON_JAR:." quiz/app/*.java

    # Check if compilation was successful
    if [ $? -eq 0 ]; then
        echo "${GREEN}Compilation successful!${RESET}"
        return 0
    else
        echo "${RED}Compilation failed!${RESET}"

        # Try alternative compilation method if the first one fails
        echo "${YELLOW}Trying alternative compilation method...${RESET}"
        cd "$APP_DIR" || { echo "${RED}Failed to change directory to $APP_DIR${RESET}"; exit 1; }
        javac -cp "$GSON_JAR" *.java

        if [ $? -eq 0 ]; then
            echo "${GREEN}Alternative compilation successful!${RESET}"
            return 0
        else
            echo "${RED}All compilation attempts failed!${RESET}"
            return 1
        fi
    fi
}

# Function to run the application
run() {
    echo "${YELLOW}Running QuizApp...${RESET}"
    cd "$FRONTEND_SRC" || { echo "${RED}Failed to change directory to $FRONTEND_SRC${RESET}"; exit 1; }

    # Run the application with the required classpath
    java -cp "$GSON_JAR:." $MAIN_CLASS
}

# Function to clean compiled class files
clean() {
    echo "${YELLOW}Cleaning compiled files...${RESET}"
    cd "$APP_DIR" || { echo "${RED}Failed to change directory to $APP_DIR${RESET}"; exit 1; }

    # Remove all class files
    rm -f *.class

    echo "${GREEN}Clean completed!${RESET}"
}

# Process command-line arguments
case "$1" in
    "compile")
        compile
        ;;
    "run")
        run
        ;;
    "clean")
        clean
        ;;
    "build-run")
        compile && run
        ;;
    *)
        echo "QuizApp Build Script"
        echo "Usage: $0 [command]"
        echo ""
        echo "Available commands:"
        echo "  compile    - Compile the application"
        echo "  run        - Run the compiled application"
        echo "  clean      - Remove compiled class files"
        echo "  build-run  - Compile and run the application"
        ;;
esac

exit 0
