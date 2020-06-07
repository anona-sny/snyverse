import { Component, ViewChild } from '@angular/core';
import { ComunikatorService } from './comunikator.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'snyverse';
  username: String = null;
  password: string = null;
  
  constructor(private comunikator: ComunikatorService) {

  }

  public login(){
    let a= this.comunikator.loginUser(this.username, this.password);
    console.log(a);
    a.subscribe(a => {
      console.log(a);
    });
  }

}
