import { Component } from '@angular/core';
import { LoginLayoutComponent } from '../../components/login-layout/login-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'login-page',
  standalone: true,
  imports: [LoginLayoutComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(){
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.email,Validators.required]),
      senha: new FormControl('', [Validators.required])
    });
  }

  submit(){
    console.log(this.loginForm.value)
  }

}
