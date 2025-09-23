/* tslint:disable:no-unused-variable */

import { TestBed, waitForAsync, inject } from '@angular/core/testing';
import { IncomingDocumentService } from './incoming-document.service';

describe('Service: IncomingDocument', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IncomingDocumentService]
    });
  });

  it('should ...', inject([IncomingDocumentService], (service: IncomingDocumentService) => {
    expect(service).toBeTruthy();
  }));
});
