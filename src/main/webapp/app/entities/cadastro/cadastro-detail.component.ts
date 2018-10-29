import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICadastro } from 'app/shared/model/cadastro.model';

@Component({
    selector: 'jhi-cadastro-detail',
    templateUrl: './cadastro-detail.component.html'
})
export class CadastroDetailComponent implements OnInit {
    cadastro: ICadastro;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
