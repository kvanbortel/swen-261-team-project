import { Injectable } from '@angular/core';
import { Message } from './Message';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  messages: Message[] = [];

  add(message: string) {
    const id: string = this.generateFakeUUID()
    const data: Message = {message: message, id: id}
    this.messages.push(data);

    setTimeout(() => {
      this.messages = this.messages.filter(m => m.id !== id);
    }, 2000);
  }

  clear() {
    this.messages = [];
  }

  generateFakeUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      const r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }
}