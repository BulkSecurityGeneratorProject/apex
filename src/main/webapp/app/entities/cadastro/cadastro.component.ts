import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICadastro } from 'app/shared/model/cadastro.model';
import { Principal } from 'app/core';
import { CadastroService } from './cadastro.service';

@Component({
    selector: 'jhi-cadastro',
    templateUrl: './cadastro.component.html'
})
export class CadastroComponent implements OnInit, OnDestroy {
    cadastros: ICadastro[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cadastroService: CadastroService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.cadastroService.query().subscribe(
            (res: HttpResponse<ICadastro[]>) => {
                this.cadastros = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCadastros();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICadastro) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInCadastros() {
        this.eventSubscriber = this.eventManager.subscribe('cadastroListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
