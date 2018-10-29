/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApexTestModule } from '../../../test.module';
import { CadastroDetailComponent } from 'app/entities/cadastro/cadastro-detail.component';
import { Cadastro } from 'app/shared/model/cadastro.model';

describe('Component Tests', () => {
    describe('Cadastro Management Detail Component', () => {
        let comp: CadastroDetailComponent;
        let fixture: ComponentFixture<CadastroDetailComponent>;
        const route = ({ data: of({ cadastro: new Cadastro(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApexTestModule],
                declarations: [CadastroDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CadastroDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CadastroDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cadastro).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
