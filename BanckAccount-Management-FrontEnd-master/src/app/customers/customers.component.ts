import { Component, OnInit } from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {catchError, Observable, throwError} from "rxjs";
import {Customer} from "../models/customer.model";
import {Form, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers: Customer[] = [];
  //customers! : Observable<Array<Customer>>;
  errorMessage! : String;
  searchFormGroup : FormGroup | undefined;

  constructor(private customerService: CustomerService, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control("")
    });
    this.loadCustomers();
    /*this.customers = this.customerService.getCustomers().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );*/
  }
  handleSearchCustomers(): void {
  const kw = this.searchFormGroup?.value.keyword;
  this.customerService.searchCustomers(kw).subscribe({
    next: (data: Customer[]) => {
      this.customers = data;
    },
    error: (err) => {
      this.errorMessage = err.message;
    }
  });
}

  /*handleSearchCustomers() {
    let kw = this.searchFormGroup?.value.keyword;

    this.customers = this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    )
  }*/

  loadCustomers(): void {
  this.customerService.getCustomers().subscribe({
    next: (data: Customer[]) => {
      this.customers = data;
    },
    error: (err) => {
      this.errorMessage = err.message;
    }
  });
}
  /*loadCustomers(): void {
    this.customerService.getCustomers().subscribe({
      next: (data) => (this.customers = data),
      error: (err) => console.error(err),
    });
  }*/

  deleteCustomer(id: number): void {
    if (confirm('Are you sure you want to delete this customer?')) {
      this.customerService.deleteCustomer(id).subscribe({
        next: () => {
          alert('Customer deleted successfully');
          this.loadCustomers(); // rafraîchit la liste
        },
        error: (err) => {
          console.error(err);
          alert('Error deleting customer');
        },
      });
    }
  }
}
