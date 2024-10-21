import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-view-change-button',
  templateUrl: './view-change-button.component.html',
  styleUrl: './view-change-button.component.css'
})
export class ViewChangeButtonComponent {
    @Input() text: string = ""
    @Input() icon: string = ""
    @Input() path: string = ""
  }
