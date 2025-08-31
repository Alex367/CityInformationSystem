import { Injectable, signal } from '@angular/core';

export interface Notification {
  type: 'success' | 'error' | 'info' | 'warning';
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  // A reactive signal that stores notifications
  notifications = signal<Notification[]>([]);

  show(type: Notification['type'], message: string) {
    const newNote = { type, message };

    // Update the signal with the new notification
    this.notifications.update(notes => [...notes, newNote]);

    // Auto-remove after 3s
    setTimeout(() => {
      this.notifications.update(notes =>
        notes.filter(n => n !== newNote)
      );
    }, 3000);
  }
}
