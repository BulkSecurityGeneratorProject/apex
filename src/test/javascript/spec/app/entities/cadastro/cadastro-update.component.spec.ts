/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApexTestModule } from '../../../test.module';
import { CadastroUpdateComponent } from 'app/entities/cadastro/cadastro-update.component';
import { CadastroService } from 'app/entities/cadastro/cadastro.service';
import { Cadastro } from 'app/shared/model/cadastro.model';

describe('Component Tests', () => {
    describe('Cadastro Management Update Component', () => {
        let comp: CadastroUpdateComponent;
        let fixture: ComponentFixture<CadastroUpdateComponent>;
        let service: CadastroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApexTestModule],
                declarations: [CadastroUpdateComponent]
            })
                .overrideTemplate(CadastroUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CadastroUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CadastroService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cadastro(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cadastro = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cadastro();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cadastro = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
