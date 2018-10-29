import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';
import { ICadastro } from 'app/shared/model/cadastro.model';
import { CadastroService } from './cadastro.service';

@Component({
    selector: 'jhi-cadastro-update',
    templateUrl: './cadastro-update.component.html'
})
export class CadastroUpdateComponent implements OnInit {
    private _cadastro: ICadastro;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private cadastroService: CadastroService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cadastro }) => {
            this.cadastro = cadastro;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.cadastro, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cadastro.id !== undefined) {
            this.subscribeToSaveResponse(this.cadastroService.update(this.cadastro));
        } else {
            this.subscribeToSaveResponse(this.cadastroService.create(this.cadastro));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICadastro>>) {
        result.subscribe((res: HttpResponse<ICadastro>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.cadastro.id    = undefined;
        this.cadastro.nome  = '';
        this.cadastro.email = '';
        this.cadastro.gif   = undefined;
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get cadastro() {
        return this._cadastro;
    }

    set cadastro(cadastro: ICadastro) {
        this._cadastro = cadastro;
    }
}
