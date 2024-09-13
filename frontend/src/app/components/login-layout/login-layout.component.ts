import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'login-layout',
  standalone: true,
  imports: [],
  templateUrl: './login-layout.component.html',
  styleUrl: './login-layout.component.scss'
})
export class LoginLayoutComponent {
    
  @Input() title = ''
  @Input() primaryButonText = ''
  @Input() secondaryButonText = ''
  @Output("submit") onSubmit = new EventEmitter();
  
  
  submit(){
    this.onSubmit.emit();
  }
}
