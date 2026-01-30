package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private EditText etName, etEmail, etCourse;
    private Button btnAdd, btnUpdate, btnDelete, btnViewAll;
    private ListView lvStudents;

    private DatabaseHelper dbHelper;
    private List<Student> studentList;
    private ArrayAdapter<String> adapter;
    private int selectedStudentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management);

        dbHelper = new DatabaseHelper(this);
        studentList = new ArrayList<>();

        initializeViews();
        setupListeners();
        loadStudents();
    }

    private void initializeViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCourse = findViewById(R.id.etCourse);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewAll = findViewById(R.id.btnViewAll);
        lvStudents = findViewById(R.id.lvStudents);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String course = etCourse.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !course.isEmpty()) {
                Student student = new Student(name, email, course);
                dbHelper.addStudent(student);
                clearFields();
                loadStudents();
                Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewAll.setOnClickListener(v -> loadStudents());

        lvStudents.setOnItemClickListener((parent, view, position, id) -> {
            Student student = studentList.get(position);
            selectedStudentId = student.getId();
            etName.setText(student.getName());
            etEmail.setText(student.getEmail());
            etCourse.setText(student.getCourse());
        });

        btnUpdate.setOnClickListener(v -> {
            if (selectedStudentId != -1) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String course = etCourse.getText().toString();

                Student student = new Student(selectedStudentId, name, email, course);
                dbHelper.updateStudent(student);
                clearFields();
                loadStudents();
                selectedStudentId = -1;
                Toast.makeText(this, "Student Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Select a student to update", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (selectedStudentId != -1) {
                dbHelper.deleteStudent(selectedStudentId);
                clearFields();
                loadStudents();
                selectedStudentId = -1;
                Toast.makeText(this, "Student Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Select a student to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadStudents() {
        studentList = dbHelper.getAllStudents();
        List<String> studentStrings = new ArrayList<>();
        for (Student s : studentList) {
            studentStrings.add(s.getName() + " - " + s.getCourse());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentStrings);
        lvStudents.setAdapter(adapter);
    }

    private void clearFields() {
        etName.setText("");
        etEmail.setText("");
        etCourse.setText("");
    }
}
