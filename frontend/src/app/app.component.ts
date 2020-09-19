import { Component, ViewChild } from '@angular/core';
import { CommunicatorService } from './communicator.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'snyverse';
  username: string = null;
  password: string = null;

  constructor(private communikator: CommunicatorService) {

  }

  public login() {
    const a = this.communikator.loginUser(this.username, this.password);
    console.log(a);
    a.subscribe(ab => {
      console.log(ab);
    });
  }

}
