import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { JwtHelper } from 'angular2-jwt';

import { CredenciaisDTO } from '../models/credenciais.dto';
import { LocalUser } from '../models/local_user';
import { API_CONFIG } from './../config/api.config';
import { StorageService } from './storage.service';

@Injectable()
export class AuthService {

  helper :  JwtHelper = new JwtHelper();

  constructor(public http : HttpClient,
              public storage : StorageService){

  }

  authenticate(creds : CredenciaisDTO){
    return this.http.post(`
                           ${API_CONFIG.baseUrl}/login`,
                           creds,
                           {
                            observe: 'response',
                            responseType : 'text'
                           });

  }

  successfulLogin(authorizationValue : string){
    // Pega o token a partir da sétima posição sem o Bearer
     let tok = authorizationValue.substring(7);
     console.log(tok);


    let user : LocalUser = {
      token: tok,
      email : this.helper.decodeToken(tok).sub
    }
    this.storage.setLocalUser(user);
    console.log(user);
  }
  logout(){
    this.storage.setLocalUser(null);
  }
  refreshToken() {
    return this.http.post(
        `${API_CONFIG.baseUrl}/auth/refresh_token`,
        {},
        {
            observe: 'response',
            responseType: 'text'
        });
}


}
