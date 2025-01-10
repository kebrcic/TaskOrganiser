# Task Organizer - Project Overview

## Introduction

The **Task Organizer** is a Java-based desktop application designed to address the communication and task management challenges faced by UNIGIS, a multinational software services company. The application simplifies client requests, streamlines communication among stakeholders, and ensures timely task assignment and completion. Built with Java Swing for GUI and SQLite for database management, this project provides role-based portals for administrators, employees, and clients, allowing efficient management of tasks and accounts.

---

## Features

### Core Functionalities:
1. **Role-Based Access**:
   - Separate login portals for administrators, employees, and clients.
   - Restricted functionalities based on user roles.

2. **Administrator Portal**:
   - Manage client, employee, and administrator accounts (create, update, delete).
   - Accept, reject, and assign tasks to employees.
   - Generate unique passwords with role-specific prefixes.

3. **Client Portal**:
   - Create, edit, and delete tasks.
   - Assign priority levels, add descriptions, and set due dates.

4. **Employee Portal**:
   - View assigned tasks.
   - Update task status and add comments.

5. **Task Management**:
   - Auto-incremented unique task IDs.
   - Flexible task filtering and search functionalities.

### Technical Highlights:
- **Hierarchical Inheritance**: Leveraged an abstract superclass for reusable methods across multiple classes.
- **Encapsulation**: Ensured secure access to sensitive data using getter and setter methods.
- **Database Integration**: SQLite used to store and retrieve account and task information.
- **GUI Implementation**: User-friendly interfaces created using Java Swing.
- **Data Validation**: Robust mechanisms to validate user inputs and ensure database integrity.

---

## Technologies Used
- **Programming Language**: Java
- **Database**: SQLite
- **Framework**: Java Swing for GUI
- **Libraries**:
  - JCalendar for date selection.
  - DbUtils for JTable integration.

---

## Success Criteria Evaluation
The system meets the success criteria defined during the planning phase, such as:
- Implementation of role-based login and corresponding portals.
- Task management functionalities for clients, employees, and administrators.
- Flexible search and filtering capabilities.
- Automated task ID generation and role-specific password generation.

---

## Future Improvements
Based on client feedback, the following enhancements can be made:
- **Design Upgrades**: Incorporate company-themed colors and icon-based navigation for improved usability.
- **Analytics**: Add a statistical analysis dashboard for administrators to monitor task progress and employee performance.
- **Notifications**: Implement email reminders for employees about upcoming deadlines using an SMTP server.

---

## How to Run
1. Clone the repository from GitHub.
2. Ensure you have Java and SQLite installed.
3. Run the `Login` class as the main entry point of the application.

---

This repository serves as a comprehensive solution to task and account management, offering a scalable and user-friendly system for businesses to streamline their operations.
