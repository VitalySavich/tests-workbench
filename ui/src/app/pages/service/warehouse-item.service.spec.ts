/* tslint:disable:no-unused-variable */

import { TestBed, waitForAsync, inject } from '@angular/core/testing';
import { WarehouseItemService } from './warehouse-item.service';

describe('Service: WarehouseItemType', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WarehouseItemService]
    });
  });

  it('should ...', inject([WarehouseItemService], (service: WarehouseItemService) => {
    expect(service).toBeTruthy();
  }));
});