import { Injectable, signal, Type } from '@angular/core';
import { Request } from './pages/request-list/request.model';
import { TypeI } from './pages/type-list/type.model';

@Injectable({
  providedIn: 'root',
})
export class SharedDataService {
  private readonly TYPE_STORAGE_KEY = 'type_id';
  private readonly REQUEST_STORAGE_KEY = 'request_data';

  public editTypeData = signal<TypeI | null>(null);
  public editRequestData = signal<Request | null>(null);

  constructor() {
    const typeStoredData = localStorage.getItem(this.TYPE_STORAGE_KEY);
    if (typeStoredData) {
      try {
        this.editTypeData.set(JSON.parse(typeStoredData) as TypeI);
      } catch (error) {
        console.error('Error parsing type stored data: ', error);
        this.editTypeData.set(null);
      }
    }

    const requestStoredData = localStorage.getItem(this.REQUEST_STORAGE_KEY);
    if (requestStoredData) {
      try {
        this.editRequestData.set(JSON.parse(requestStoredData) as Request);
      } catch (error) {
        console.error('Error parsing request stored data: ', error);
        this.editRequestData.set(null);
      }
    }
  }

  setTypeEdit(data: TypeI) {
    this.editTypeData.set(data);
    localStorage.setItem(this.TYPE_STORAGE_KEY, JSON.stringify(data));
  }

  setRequestEdit(data: Request) {
    this.editRequestData.set(data);
    localStorage.setItem(this.REQUEST_STORAGE_KEY, JSON.stringify(data));
  }

  clearRequest() {
    this.editTypeData.set(null);
    this.editRequestData.set(null);
    localStorage.removeItem(this.TYPE_STORAGE_KEY);
    localStorage.removeItem(this.REQUEST_STORAGE_KEY);
  }
}
