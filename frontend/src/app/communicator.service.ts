import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommunicatorService {
  private baseurl = 'http://localhost:80/users/login';  // URL to web api

  constructor(private http: HttpClient) { }

  loginUser(username: string, password: string) {
    let body = {
      username: username,
      password: password
    };
    const headers = new HttpHeaders()
    .set('Content-Type', 'application/json; charset=utf-8')
    .set('Access-Control-Allow-Origin', '*');
    return this.http.post<string>(this.baseurl, body, {headers: headers});
  }
}
