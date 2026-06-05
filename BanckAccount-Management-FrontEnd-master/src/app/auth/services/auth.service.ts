import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isAuthenticated = false;

  login(username: string, password: string): boolean {
    // 👉 version simple (test)
    if (username === 'admin' && password === 'admin') {
      this.isAuthenticated = true;
      localStorage.setItem('auth', 'true');
      return true;
    }
    return false;
  }

  logout(): void {
    this.isAuthenticated = false;
    localStorage.removeItem('auth');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('auth') === 'true';
  }
}
