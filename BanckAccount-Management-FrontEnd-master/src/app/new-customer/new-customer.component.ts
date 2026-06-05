import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";

import { CustomerService } from "../services/customer.service";
import { Customer } from "../models/customer.model";



@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit {

  newCustomerFormGroup! : FormGroup;

  //constructor(private fb : FormBuilder) { }

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name : this.fb.control(null),
      email : this.fb.control(null),
    });
  }

   handleSaveCustomer(): void {
    const customer: Customer = this.newCustomerFormGroup.value;

    this.customerService.saveCustomer(customer).subscribe({
      next: (savedCustomer: Customer) => {
        console.log(savedCustomer); // évite l’erreur "data never read"
        alert("Customer saved successfully");
        this.newCustomerFormGroup.reset();
      },
      error: (err: any) => { // typage explicite
        console.error(err);
        alert("Error while saving customer");
      }
    });
  }

}
