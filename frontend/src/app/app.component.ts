import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from './api.service';
import { Employee } from './employee';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <main>
      <h1>Enterprise Employee Management</h1>

      <section *ngIf="!loggedIn">
        <h2>Login</h2>
        <input [(ngModel)]="email" placeholder="Email">
        <input [(ngModel)]="password" placeholder="Password" type="password">
        <button (click)="login()">Login</button>
        <p>{{message}}</p>
      </section>

      <section *ngIf="loggedIn">
        <button (click)="logout()">Logout</button>

        <h2>Employees</h2>
        <button (click)="loadEmployees()">Refresh</button>

        <div class="form">
          <input [(ngModel)]="newEmployee.firstName" placeholder="First name">
          <input [(ngModel)]="newEmployee.lastName" placeholder="Last name">
          <input [(ngModel)]="newEmployee.email" placeholder="Email">
          <input [(ngModel)]="newEmployee.department" placeholder="Department">
          <input [(ngModel)]="newEmployee.jobTitle" placeholder="Job title">
          <input [(ngModel)]="newEmployee.location" placeholder="Location">
          <button (click)="create()">Add employee</button>
        </div>

        <table>
          <thead>
            <tr>
              <th>Name</th><th>Email</th><th>Department</th><th>Job Title</th><th></th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let e of employees">
              <td>{{e.firstName}} {{e.lastName}}</td>
              <td>{{e.email}}</td>
              <td>{{e.department}}</td>
              <td>{{e.jobTitle}}</td>
              <td><button *ngIf="e.id" (click)="remove(e.id!)">Delete</button></td>
            </tr>
          </tbody>
        </table>

        <h2>Employee Assistant</h2>
        <input [(ngModel)]="question" placeholder="Ask about employee data">
        <button (click)="ask()">Ask</button>
        <p>{{answer}}</p>
      </section>
    </main>
  `
})
export class AppComponent implements OnInit {
  email = '';
  password = '';
  message = '';
  employees: Employee[] = [];
  question = '';
  answer = '';

  newEmployee: Employee = {
    firstName: '', lastName: '', email: '', department: '', jobTitle: '',
    location: '', manager: '', employmentStatus: 'ACTIVE'
  };

  constructor(private api: ApiService) {}

  get loggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  ngOnInit(): void {
    if (this.loggedIn) this.loadEmployees();
  }

  login(): void {
    this.api.login(this.email, this.password).subscribe({
      next: r => {
        localStorage.setItem('token', r.token);
        this.message = '';
        this.loadEmployees();
      },
      error: () => this.message = 'Login failed'
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    this.employees = [];
  }

  loadEmployees(): void {
    this.api.employees().subscribe({
      next: data => this.employees = data,
      error: () => this.message = 'Could not load employees'
    });
  }

  create(): void {
    this.api.createEmployee(this.newEmployee).subscribe(() => {
      this.newEmployee = {
        firstName: '', lastName: '', email: '', department: '', jobTitle: '',
        location: '', manager: '', employmentStatus: 'ACTIVE'
      };
      this.loadEmployees();
    });
  }

  remove(id: string): void {
    this.api.deleteEmployee(id).subscribe(() => this.loadEmployees());
  }

  ask(): void {
    this.api.askAssistant(this.question).subscribe(r => this.answer = r.answer);
  }
}
