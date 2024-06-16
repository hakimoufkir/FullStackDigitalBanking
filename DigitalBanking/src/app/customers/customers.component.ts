import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent {
  customers!: Observable<Customer[]>;
  errorMessage!: Object

  searchFormGroup : FormGroup | undefined;
  constructor (private customerService: CustomerService, private fb : FormBuilder) { }


  ngOnInit(): void {
    this.searchFormGroup=this.fb.group({
      keyword : this.fb.control("")
    });



    this.customers=this.customerService.getCustomers().pipe(
      catchError(err => {
      this.errorMessage=err.message;
      return throwError(err);
    })
  );
  }
  handleSearchCustomers(){
    let kw = this.searchFormGroup?.value.keyword;
    this.customers=this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errorMessage=err.message;
        return throwError(err);
      })
    );
  }

  handleDeleteCustomer(c : Customer){
    let conf = confirm("are you sure?")
    if (!conf) {return;}
    this.customerService.deleteCustomer(c.id).subscribe ({
      next: (resp)=>{
        this.customers= this.customers.pipe(
          map(data=>{
            let index= data.indexOf(c);
            data.slice(index,1)
            return data;
          })
        )
    }
    })
  }

  }
