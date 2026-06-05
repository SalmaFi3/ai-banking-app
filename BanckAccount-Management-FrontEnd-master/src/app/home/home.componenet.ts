import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../services/customer.service';
import { Customer } from '../models/customer.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  totalCustomers: number = 0;
  errorMessage: string = '';

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.customerService.getCustomers().subscribe({
      next: (customers: Customer[]) => {
        this.totalCustomers = customers.length;
      },
      error: (err) => {
        this.errorMessage = err.message;
      }
    });
  }
}
