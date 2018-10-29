import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICadastro } from 'app/shared/model/cadastro.model';
import { CadastroService } from './cadastro.service';

@Component({
    selector: 'jhi-cadastro-delete-dialog',
    templateUrl: './cadastro-delete-dialog.component.html'
})
export class CadastroDeleteDialogComponent {
    cadastro: ICadastro;

    constructor(private cadastroService: CadastroService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cadastroService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cadastroListModification',
                content: 'Deleted an cadastro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cadastro-delete-popup',
    template: ''
})
export class CadastroDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cadastro }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CadastroDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cadastro = cadastro;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
