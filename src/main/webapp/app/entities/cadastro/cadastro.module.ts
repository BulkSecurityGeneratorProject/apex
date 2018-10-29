import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApexSharedModule } from 'app/shared';
import {
    CadastroComponent,
    CadastroDetailComponent,
    CadastroUpdateComponent,
    CadastroDeletePopupComponent,
    CadastroDeleteDialogComponent,
    cadastroRoute,
    cadastroPopupRoute
} from './';

const ENTITY_STATES = [...cadastroRoute, ...cadastroPopupRoute];

@NgModule({
    imports: [ApexSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CadastroComponent,
        CadastroDetailComponent,
        CadastroUpdateComponent,
        CadastroDeleteDialogComponent,
        CadastroDeletePopupComponent
    ],
    entryComponents: [CadastroComponent, CadastroUpdateComponent, CadastroDeleteDialogComponent, CadastroDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApexCadastroModule {}
