import { API_CONFIG } from '../../config/api.config';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import { StorageService } from '../storage.service';
import { ClienteDTO } from '../../models/cliente.dto';

@Injectable()
export class ClienteService {

  constructor(public http : HttpClient,
              public storage : StorageService){


        }
        findByEmail(email : string) : Observable<ClienteDTO>{

            return this.http.get<ClienteDTO>(`${API_CONFIG.baseUrl}/clientes/email?value=${email}`);
        }

        getImageFromBucket(id : string) : Observable<any> {
          let url = `${API_CONFIG.bucketBaseUrl}/cp${id}.jpg`
          return this.http.get(url, {responseType : 'blob'});
      }


}
