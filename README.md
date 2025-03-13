# Serenity Mental Health Therapy Center System

## Overview
The **Serenity Mental Health Therapy Center System** is a digital solution designed to streamline the management of patient registrations, therapy programs, therapists, payments, and therapy sessions for the Serenity Mental Health Therapy Center. The system aims to replace the manual paper-based process with an efficient and secure digital platform.

## Features
1. **User Role Management**:
   - **Admin**: Manages therapists and therapy programs.
   - **Receptionist**: Manages patients, schedules therapy sessions, and processes payments.
   - Secure authentication and authorization for both roles.

2. **Therapist Management**:
   - Add, update, delete, and view therapist details.
   - Assign therapists to specific therapy programs.
   - Track therapist schedules and availability.

3. **Therapy Program Management**:
   - Create, modify, and remove therapy programs.
   - Define program details (name, duration, cost, description).
   - Link programs to therapists for proper scheduling.

4. **Patient Management**:
   - Add, update, delete, and view patient profiles.
   - Store medical history and therapy records.
   - Search and filter patients based on therapy sessions.

5. **Therapy Session Scheduling**:
   - Book therapy appointments for patients.
   - Assign available therapists based on schedule.
   - Reschedule or cancel appointments as needed.

6. **Payment & Invoice Management**:
   - Process payments for therapy sessions.
   - Generate and print invoices for patients.
   - Track pending and completed transactions.

7. **Reporting & Analytics**:
   - Admin: View reports on therapist performance and therapy session statistics.
   - Receptionist: Generate financial reports and track payments.
   - Both roles can access patient therapy history (read-only).

8. **Secure Data Management**:
   - Password encryption using BCrypt.
   - Role-based access control.
   - Database relationships managed through Hibernate ORM.

## Technologies Used
- **Backend**: Java, Hibernate ORM.
- **Database**: MySQL.
- **Security**: BCrypt for password encryption.
- **Architecture**: Layered architecture with Façade/Factory design patterns.

## Database Schema
The database consists of the following tables:
1. **User**: Stores Admin and Receptionist login details.
2. **Therapist**: Stores therapist details.
3. **TherapyProgram**: Stores therapy program details.
4. **Patient**: Stores patient details.
5. **TherapySession**: Stores therapy session details.
6. **Payment**: Stores payment details.
7. **TherapistProgram**: Many-to-Many relationship between therapists and therapy programs.
8. **PatientProgram**: Many-to-Many relationship between patients and therapy programs.

## Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)**: Ensure JDK 8 or higher is installed.
2. **MySQL**: Install MySQL and create a database named `SerenityTherapyCenter`.
3. **IDE**: Use an IDE like IntelliJ IDEA or Eclipse for development.

### Steps to Run the Project
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd serenity-therapy-center
