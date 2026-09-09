import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private base = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  register(payload: any): Observable<{token: string}> {
    return this.http.post<{token: string}>(`${this.base}/auth/register`, payload);
  }

  login(email: string, password: string): Observable<{token: string}> {
    return this.http.post<{token: string}>(`${this.base}/auth/login`, { email, password });
  }

  employees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.base}/employees`);
  }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(`${this.base}/employees`, employee);
  }

  deleteEmployee(id: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/employees/${id}`);
  }

  askAssistant(q: string): Observable<{answer: string}> {
    return this.http.get<{answer: string}>(`${this.base}/assistant/ask`, { params: { q } });
  }
}
