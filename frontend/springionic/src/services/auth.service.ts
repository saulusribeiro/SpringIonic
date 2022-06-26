import { API_CONFIG } from './../config/api.config';
import { Injectable } from "@angular/core";
import { CredenciaisDTO } from '../models/credenciais.dto';
import { HttpClient } from '@angular/common/http';
import { LocalUser } from '../models/local_user';
import { StorageService } from './storage.service';

@Injectable()
export class AuthService {

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
      token: tok
    }
    this.storage.setLocalUser(user);
    console.log(user);
  }
  logout(){
    this.storage.setLocalUser(null);
  }

}
