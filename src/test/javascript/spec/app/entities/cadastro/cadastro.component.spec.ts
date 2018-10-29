/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ApexTestModule } from '../../../test.module';
import { CadastroComponent } from 'app/entities/cadastro/cadastro.component';
import { CadastroService } from 'app/entities/cadastro/cadastro.service';
import { Cadastro } from 'app/shared/model/cadastro.model';

describe('Component Tests', () => {
    describe('Cadastro Management Component', () => {
        let comp: CadastroComponent;
        let fixture: ComponentFixture<CadastroComponent>;
        let service: CadastroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApexTestModule],
                declarations: [CadastroComponent],
                providers: []
            })
                .overrideTemplate(CadastroComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CadastroComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CadastroService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Cadastro(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cadastros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
