package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {                                                
  ArrayList<String> studentClasses;
  double studentGPA;
  String studentGender;
  int classNumber;

  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.studentClasses = classes;
    this.studentGPA = gpa;
    this.studentGender = gender;
    this.classNumber = classes.size();
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {
    return "This class is too much work";
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    if(this.classNumber == 0){
      return this.name + " has a GPA of " + this.studentGPA + " and is not taking any classes. Somehow, he says, \"" + this.says() + "\".";
    }
    String result = this.name + " has a GPA of " + this.studentGPA + " and is taking " + this.classNumber + " classes: ";
    for(int i = 0; i < this.classNumber - 1; ++i){
      result += (this.studentClasses.get(i) + ", ");
    }
    result += ("and, " + this.studentClasses.get(this.classNumber-1) + ". He says, \"" + this.says() + "\".");
    return result;
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    // Check minimum number of arguments
    int argumentNumber = args.length;
    if(argumentNumber < 3){
      System.out.println("Missing command line arguments");
      System.exit(-1);
    }

    // Populate classList
    ArrayList<String> classList = new ArrayList<String>();
    for(int i = 3; i < argumentNumber; ++i){
      classList.add(args[i]);
    }

    // Make the person and print them out
    Student person = new Student(args[0], classList, Double.parseDouble(args[2]), args[1]);
    System.out.println(person);
    System.exit(0);
  }
}