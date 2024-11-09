import { Component, ViewChild } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { AdminInfo } from '../AdminInfo';
import { BehaviorSubject } from 'rxjs';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-admin-dash',
  templateUrl: './admin-dash.component.html',
  styleUrl: './admin-dash.component.css'
})
export class AdminDashComponent {
  @ViewChild('chartRegionCanvas', { static: false }) chartRegionCanvas : any;
  @ViewChild('chartFundingCanvas', { static: false }) chartFundingCanvas : any;
  
  constructor(public authService: AuthService){}

  adminInfo$ = new BehaviorSubject<AdminInfo | null>(null);
  public chartRegion: any;
  public chartFunding: any;
  regionChartLabels: string[] = [];
  regionChartData: number[] = [];
  fundingChartData: number[] = [];
  regionCharting: boolean = false;
  fundCharting: boolean = false;

  ngAfterViewChecked() {
    if (this.chartRegionCanvas && !this.chartRegion) {
      this.createRegionChart();
    }

    if (this.chartFundingCanvas && !this.chartFunding) {
      this.createFundingChart();
    }
  }

  ngOnInit(): void {
    this.authService.getAdminInfo().subscribe({
      next: (response: AdminInfo) => {
        this.adminInfo$.next(response);
        console.log(response);
        // add to pie chart
        if(response?.regions){
          Object.keys(response.regions).forEach(key => {
            if(key != "NONE"){
              this.regionChartLabels.push(key.toLowerCase()
              .replace(/_/g, ' ')
              .replace(/\b\w/g, char => char.toUpperCase()))
              this.regionChartData.push(response.regions[key as keyof AdminInfo])
              this.fundingChartData.push(response.fundedByRegion[key as keyof AdminInfo])
              if(response.regions[key as keyof AdminInfo] > 0){
                this.regionCharting = true;
              }
              if(response.fundedByRegion[key as keyof AdminInfo] > 0){
                this.fundCharting = true;
              }
            }
          });
        }
      },
    });
  }

  createRegionChart(){
    if (this.chartRegion) {
      this.chartRegion.destroy();
    }
    this.chartRegion = new Chart(this.chartRegionCanvas.nativeElement, {
      type: 'pie', 

      data: {
        labels: this.regionChartLabels,
           datasets: [{
    label: 'Helpers in Region',
    data: this.regionChartData,
    backgroundColor:  [   
    'rgba(255, 99, 132, 0.7)',  // Red
    'rgba(54, 162, 235, 0.7)',  // Blue
    'rgba(255, 206, 86, 0.7)',  // Yellow
    'rgba(75, 192, 192, 0.7)',  // Teal
    'rgba(215, 188, 185, 0.7)', // Misty Rose
    'rgba(255, 159, 64, 0.7)',  // Orange
    'rgba(140, 140, 140, 0.7)', // Grey
    'rgba(255, 99, 71, 0.7)',   // Tomato
    'rgba(186, 85, 211, 0.7)',  // Medium Orchid
    'rgba(0, 128, 128, 0.7)',   // Teal Dark
    'rgba(127, 255, 0, 0.7)',   // Chartreuse
    'rgba(255, 215, 0, 0.7)',   // Gold
    'rgba(70, 130, 180, 0.7)',  // Steel Blue
    'rgba(152, 251, 152, 0.7)', // Pale Green
    'rgba(210, 105, 30, 0.7)',   // Chocolate
    'rgba(144, 238, 144, 0.7)', // Light Green
    'rgba(32, 178, 170, 0.7)',  // Light Sea Green
    'rgba(100, 149, 237, 0.7)', // Cornflower Blue
    'rgba(255, 140, 0, 0.7)',   // Dark Orange
           ],
    hoverOffset: 4
  }],
      },
      options: {
        aspectRatio:2.5
      }

    });
  }

  createFundingChart(){
    if (this.chartFunding) {
      this.chartFunding.destroy();
    }
    this.chartFunding = new Chart(this.chartFundingCanvas.nativeElement, {
      type: 'pie', 

      data: {
        labels: this.regionChartLabels,
           datasets: [{
    label: 'Amount Funded',
    data: this.fundingChartData,
    backgroundColor:  [   
      'rgba(255, 99, 132, 0.7)',  // Red
    'rgba(54, 162, 235, 0.7)',  // Blue
    'rgba(255, 206, 86, 0.7)',  // Yellow
    'rgba(75, 192, 192, 0.7)',  // Teal
    'rgba(215, 188, 185, 0.7)', // Misty Rose
    'rgba(255, 159, 64, 0.7)',  // Orange
    'rgba(140, 140, 140, 0.7)', // Grey
    'rgba(255, 99, 71, 0.7)',   // Tomato
    'rgba(186, 85, 211, 0.7)',  // Medium Orchid
    'rgba(0, 128, 128, 0.7)',   // Teal Dark
    'rgba(127, 255, 0, 0.7)',   // Chartreuse
    'rgba(255, 215, 0, 0.7)',   // Gold
    'rgba(70, 130, 180, 0.7)',  // Steel Blue
    'rgba(152, 251, 152, 0.7)', // Pale Green
    'rgba(210, 105, 30, 0.7)',   // Chocolate
    'rgba(144, 238, 144, 0.7)', // Light Green
    'rgba(32, 178, 170, 0.7)',  // Light Sea Green
    'rgba(100, 149, 237, 0.7)', // Cornflower Blue
    'rgba(255, 140, 0, 0.7)',   // Dark Orange
           ],
    hoverOffset: 4
  }],
      },
      options: {
        aspectRatio:2.5,
        plugins: {
          tooltip: {
            callbacks: {
              label: function(tooltipItem) {
                // Format the value as currency
                const value = tooltipItem.raw as number; // raw contains the data value
                return `Funded: ${new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value)}`;
              }
            }
          }
        }
      }

    });
  }
}
