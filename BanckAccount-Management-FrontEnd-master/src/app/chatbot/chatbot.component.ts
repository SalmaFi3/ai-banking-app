import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent {
  query: string = '';
  response: string = '';
  loading: boolean = false;
  error: string | null = null;

  constructor(private http: HttpClient) {}

  askAgent(): void {
    const trimmed = this.query.trim();
    if (!trimmed) {
      return;
    }

    this.response = '';
    this.error = null;
    this.loading = true;

    const url = `http://localhost:8081/askAgent?query=${encodeURIComponent(trimmed)}`;
    
    this.http.get(url, {
      responseType: 'text'
    }).subscribe({
      next: (response) => {
        this.response = response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur complète:', err);
        this.error = err?.error?.message || 
                    err?.message || 
                    'Erreur de connexion. Vérifiez que le backend bdcc-ai-agent est démarré sur le port 8081.';
        this.loading = false;
        this.response = '❌ ' + this.error;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}

