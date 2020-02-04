/**
 * @author Ahmet Gedemenli
 *
 * Created for CMPE 321 - Project 2
 * 2014400147
 */

import java.io.*;
import java.util.*;

public class storageManager {

  private static Scanner reader;

  private static Scanner dbReader;

  private static BufferedWriter writer;

  private static BufferedWriter dbWriter;

  private static ArrayList<RecordType> typeList = new ArrayList<>();

  private static ArrayList<Page> pages = new ArrayList<>();

  private int pageCnt = 0;

  public static void main(String[] args) {
    storageManager manager = new storageManager();
    manager.ioHandler(args);
    manager.readDB();
    while (reader.hasNext()) {
      manager.getInputLine();
    }
    manager.writeDB();
    manager.close();
  }

  /**
   * Gets the input necessary to create a type.
   * Creates the type.
   */
  private void createType() {
    String name = reader.next();
    int numberOfFields = reader.nextInt();
    for (int i = 0; i < numberOfFields; i++) {
      reader.next();
    }
    RecordType newType = new RecordType(name, numberOfFields);
    typeList.add(newType);
  }

  /**
   * Gets the input necessary to create a record.
   * Creates the record.
   */
  private void createRecord() {
    String typeName = reader.next();
    RecordType recordType = typeList.get(0);
    for (RecordType type : typeList) {
      if (type.getTypeName().equals(typeName)) {
        recordType = type;
      }
    }
    RecordHeader recordHeader = new RecordHeader(recordType);
    Record newRecord = new Record(recordHeader);
    ArrayList<Integer> fields = new ArrayList<>();
    for (int i = 0; i < recordType.getNumberOfFields(); i++) {
      fields.add(reader.nextInt());
    }
    newRecord.setFields(fields);
    for (Page page : pages) {
      if (page.getRecords().get(0).getRecordHeader().getRecordType().getTypeName().equals(typeName)) {
        page.getRecords().add(newRecord);
        Collections.sort(page.getRecords());
        page.getPageHeader().setNumberOfRecords(page.getPageHeader().getNumberOfRecords() + 1);
        return;
      }
    }
    PageHeader pageHeader = new PageHeader(pageCnt++);
    pageHeader.setNumberOfRecords(1);
    pageHeader.setEmpty(false);
    Page page = new Page(pageHeader);
    ArrayList<Record> records = new ArrayList<>();
    records.add(newRecord);
    page.setRecords(records);
    pages.add(page);
  }

  /**
   * Gets the input necessary to delete a type.
   * Deletes the type.
   */
  private void deleteType() {
    String typeName = reader.next();
    for (int i = 0; i < typeList.size(); i++)
      if (typeList.get(i).getTypeName().equals(typeName)) {
        typeList.remove(i);
        break;
      }
    for (int i = 0; i < pages.size(); i++) {
      if (pages.get(i).getRecords().get(0).getRecordHeader().getRecordType().getTypeName().equals(typeName)) {
        pages.remove(i);
        break;
      }
    }
  }

  /**
   * Gets the input necessary to delete a record.
   * Deletes the record by primary key.
   */
  private void deleteRecord() {
    String typeName = reader.next();
    int primaryKey = reader.nextInt();
    for (Page page : pages) {
      if (page.getRecords().get(0).getRecordHeader().getRecordType().getTypeName().equals(typeName)) {
        for (Record record : page.getRecords()) {
          if (record.getFields().get(0) == primaryKey) {
            page.getRecords().remove(record);
            page.getPageHeader().setNumberOfRecords(page.getPageHeader().getNumberOfRecords() - 1);
            if (page.getRecords().size() == 0) {
              pages.remove(page);
            }
            break;
          }
        }
      }
    }
  }

  /**
   * Sorts and lists all types to the output file.
   */
  private void listType() {
    ArrayList<String> clone = new ArrayList<>();
    for (RecordType type : typeList) {
      clone.add(type.getTypeName());
    }
    Collections.sort(clone);
    for (String type : clone) {
      write(type + "\n");
    }
  }

  /**
   * Gets the input necessary to list records of a type.
   * Lists all records to the output file.
   */
  private void listRecord() {
    String typeName = reader.next();
    for (Page page : pages) {
      if (page.getRecords().get(0).getRecordHeader().getRecordType().getTypeName().equals(typeName)) {
        for (Record record : page.getRecords()) {
          ArrayList<Integer> fields = record.getFields();
          write("" + fields.get(0));
          for (int i = 1; i < fields.size(); i++)
            write(" " + fields.get(i));
          write("\n");
        }
      }
    }
  }

  /**
   * Gets the input necessary to search a record.
   * Searches the record by primary key.
   * Outputs the result to the output file.
   */
  private void searchRecord() {
    reader.next();
    String typeName = reader.next();
    for (Page page : pages) {
      if (page.getRecords().get(0).getRecordHeader().getRecordType().getTypeName().equals(typeName)) {
        int primaryKey = reader.nextInt();
        for (Record record : page.getRecords()) {
          if (record.getFields().get(0) == primaryKey) {
            write(primaryKey + "");
            for (int i = 1; i < record.getFields().size(); i++) {
              write(" " + record.getFields().get(i));
            }
            write("\n");
          }
        }
      }
    }
  }

  /**
   * Gets the input necessary to update a record.
   * Updates the record with the given primary key.
   */
  private void updateRecord() {
    reader.next();
    String typeName = reader.next();
    for (Page page : pages) {
      if (page.getRecords().get(0).getRecordHeader().getRecordType().getTypeName().equals(typeName)) {
        int primaryKey = reader.nextInt();
        for (Record record : page.getRecords()) {
          if (record.getFields().get(0) == primaryKey) {
            for (int i = 1; i < record.getFields().size(); i++) {
              record.getFields().set(i, reader.nextInt());
            }
          }
        }
      }
    }
  }

  /**
   * Gets input for determining whether it's
   * record or type.
   * Calls the proper method for given input.
   */
  private void getCreateOperation() {
    if (reader.next().equals("type")) {
      createType();
    } else {
      createRecord();
    }
  }

  /**
   * Gets input for determining whether it's
   * record or type.
   * Calls the proper method for given input.
   */
  private void getDeleteOperation() {
    if (reader.next().equals("type")) {
      deleteType();
    } else {
      deleteRecord();
    }
  }

  /**
   * Gets input for determining whether it's
   * record or type.
   * Calls the proper method for given input.
   */
  private void getListOperation() {
    if (reader.next().equals("type")) {
      listType();
    } else {
      listRecord();
    }
  }

  /**
   * Gets any type of input line.
   * Calls the proper method for given input.
   */
  private void getInputLine() {
    String operation = reader.next();
    if (operation.equals("create")) {
      getCreateOperation();
    }
    if (operation.equals("delete")) {
      getDeleteOperation();
    }
    if (operation.equals("list")) {
      getListOperation();
    }
    if (operation.equals("search")) {
      searchRecord();
    }
    if (operation.equals("update")) {
      updateRecord();
    }
  }

  /**
   * Sets the scanner and the writer for given
   * input and output files.
   * Sets the scanner and the writer for db file.
   *
   * @param args input and output file names.
   */
  private void ioHandler(String[] args) {
    File inputFile = new File(args[0]);
    try {
      reader = new Scanner(inputFile);
      writer = new BufferedWriter(new FileWriter(args[1]));
    } catch (IOException e) {
      System.out.println(e);
      System.exit(0);
    }
    try {
      dbReader = new Scanner(new File("database.txt"));
    } catch (FileNotFoundException e) {
      try {
        BufferedWriter dummy = new BufferedWriter(new FileWriter("database.txt"));
        dummy.write("0 0");
        dummy.close();
        dbReader = new Scanner(new File("database.txt"));
      } catch (IOException e1) {
        e1.printStackTrace();
        System.exit(0);
      }
    }
  }

  /**
   * Outputs given string to the output file.
   * Handles the exception.
   */
  private void write(String s) {
    try {
      writer.write(s);
    } catch (IOException e) {
      System.exit(0);
    }
  }

  /**
   * Gets the data from database.txt
   */
  private void readDB() {
    int numberOfPages = dbReader.nextInt();
    for (int i = 0; i < numberOfPages; i++) {
      Page page = new Page(new PageHeader(++pageCnt));
      dbReader.next();
      page.getPageHeader().setNumberOfRecords(dbReader.nextInt());
      dbReader.next();
      dbReader.next();
      ArrayList<Record> records = new ArrayList<>();
      for (int j = 0; j < page.getPageHeader().getNumberOfRecords(); j++) {
        String typeName = dbReader.next();
        int numberOfFields = dbReader.nextInt();
        Record record = new Record(new RecordHeader(new RecordType(typeName, numberOfFields)));
        ArrayList<Integer> fields = new ArrayList<>();
        for (int k = 0; k < numberOfFields; k++) {
          fields.add(dbReader.nextInt());
        }
        record.setFields(fields);
        records.add(record);
      }
      page.setRecords(records);
      pages.add(page);
    }
    int numberOfTypes = dbReader.nextInt();
    for (int i = 0; i < numberOfTypes; i++) {
      String typeName = dbReader.next();
      int numberOfFields = dbReader.nextInt();
      typeList.add(new RecordType(typeName, numberOfFields));
    }
  }

  /**
   * Writes the data to database.txt
   */
  private void writeDB() {
    try {
      dbWriter = new BufferedWriter(new FileWriter("database.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    writerDB(pages.size() + " ");
    for (Page page : pages) {
      writerDB(page.toString());
    }
    writerDB(typeList.size() + " ");
    for (RecordType type : typeList) {
      writerDB(type.toString());
    }
  }

  /**
   * Outputs given string to database.txt
   */
  private void writerDB(String s) {
    try {
      dbWriter.write(s);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Closes the writers.
   */
  private void close() {
    try {
      writer.close();
      dbWriter.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
