import {Component, OnInit} from '@angular/core';

interface CreditPackage {
  id: number;
  credits: number;
  price: string;
}
@Component({
  selector: 'app-subscriptions',
  templateUrl: './plan.component.html',
  styleUrl: './plan.component.css'
})
export class PlanComponent implements OnInit {
  tabs = [
    {title: 'purchaseCredit.about', content: 'Content 1', value: '0'},
    {title: 'purchaseCredit.termService', content: 'Content 2', value: '1'}
  ];

  creditPackages: CreditPackage[] = [
    {id: 1, credits: 100, price: '10.000.000 vnđ'},
    {id: 2, credits: 100, price: '10.000.000 vnđ'},
    {id: 3, credits: 100, price: '10.000.000 vnđ'},
    {id: 4, credits: 100, price: '10.000.000 vnđ'},
    {id: 5, credits: 100, price: '10.000.000 vnđ'}
  ];

  selectedPackageId: number | null = null;
  balance: number = 0;
  constructor() {}
  ngOnInit(): void {
    this.getBalance();
  }
  selectPackage(id: number): void {
    this.selectedPackageId = id;
  }

  onBuyCredit(): void {
    if (this.selectedPackageId) {
      // Handle purchase logic
    }
  }
  getBalance(): void {
    this.balance = 1500;
  }
}
