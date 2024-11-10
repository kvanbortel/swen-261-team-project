import {Component, Input} from '@angular/core';
import {ProfileSectionService} from "../profile-section.service";
import {Region} from "../region";
import {ProfileInfo} from "../profile-info";

@Component({
  selector: 'app-profile-section',
  templateUrl: './profile-section.component.html',
  styleUrl: './profile-section.component.css'
})
export class ProfileSectionComponent {

  @Input() other: boolean = false;
  @Input() accountName!: string;
  @Input() profileInfo!: ProfileInfo;

  constructor(private profileSectionService: ProfileSectionService) {}

  protected readonly Region = Region;
}
