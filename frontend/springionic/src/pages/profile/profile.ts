import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';


import { StorageService } from '../../services/storage.service';
import { ClienteDTO } from './../../models/cliente.dto';
import { ClienteService } from '../../services/domain/cliente.service';
import { HttpClient } from '@angular/common/http';
import { API_CONFIG } from '../../config/api.config';




@IonicPage()
@Component({
  selector: 'page-profile',
  templateUrl: 'profile.html',
})
export class ProfilePage {

  cliente : ClienteDTO;

  constructor(public navCtrl: NavController,
             public navParams: NavParams,
             public storage : StorageService,
             public clienteService : ClienteService,
             public http : HttpClient,) {
  }

  ionViewDidLoad() {
    let localUser = this.storage.getLocalUser();
    if(localUser && localUser.email) {
       this.clienteService.findByEmail(localUser.email)
       .subscribe(response => {
        this.cliente = response; /// recebe clienteDTO
        this.getImageIfExists();
     },
      error => {
        if (error.status = 403 ){
          this.navCtrl.setRoot('HomePage');
        }
      });
      }
  }
  getImageIfExists() {
    this.clienteService.getImageFromBucket(this.cliente.id)
    .subscribe(response => {
      this.cliente.imageUrl = `${API_CONFIG.bucketBaseUrl}/cp${this.cliente.id}.jpg`;
      },
    error => {});
  }


}
