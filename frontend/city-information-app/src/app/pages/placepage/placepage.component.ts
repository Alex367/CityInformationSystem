import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-placepage',
  imports: [FormsModule],
  templateUrl: './placepage.component.html',
  styleUrl: './placepage.component.css'
})
export class PlacePageComponent implements OnInit{
  enteredSelectedCity = '';
  enteredSelectedType = '';
  enteredPlacename = '';
  enteredPlaceAvatar = '';
  enteredPlaceDescription = '';
  isFilledParams = signal(false);
  

  ngOnInit(): void {
    
  }

  onSubmit(){
    console.log('sub')
  }

}
