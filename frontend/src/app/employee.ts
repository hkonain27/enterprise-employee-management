export interface Employee {
  id?: string;
  firstName: string;
  lastName: string;
  email: string;
  department: string;
  jobTitle: string;
  location?: string;
  manager?: string;
  employmentStatus?: string;
}
