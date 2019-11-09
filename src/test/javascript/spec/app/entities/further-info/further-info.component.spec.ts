import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebTestModule } from '../../../test.module';
import { FurtherInfoComponent } from 'app/entities/further-info/further-info.component';
import { FurtherInfoService } from 'app/entities/further-info/further-info.service';
import { FurtherInfo } from 'app/shared/model/further-info.model';

describe('Component Tests', () => {
  describe('FurtherInfo Management Component', () => {
    let comp: FurtherInfoComponent;
    let fixture: ComponentFixture<FurtherInfoComponent>;
    let service: FurtherInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [FurtherInfoComponent],
        providers: []
      })
        .overrideTemplate(FurtherInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FurtherInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FurtherInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FurtherInfo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.furtherInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
