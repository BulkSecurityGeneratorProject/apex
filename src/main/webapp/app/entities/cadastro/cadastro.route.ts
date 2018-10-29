import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Cadastro } from 'app/shared/model/cadastro.model';
import { CadastroService } from './cadastro.service';
import { CadastroComponent } from './cadastro.component';
import { CadastroDetailComponent } from './cadastro-detail.component';
import { CadastroUpdateComponent } from './cadastro-update.component';
import { CadastroDeletePopupComponent } from './cadastro-delete-dialog.component';
import { ICadastro } from 'app/shared/model/cadastro.model';

@Injectable({ providedIn: 'root' })
export class CadastroResolve implements Resolve<ICadastro> {
    constructor(private service: CadastroService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((cadastro: HttpResponse<Cadastro>) => cadastro.body));
        }
        return of(new Cadastro());
    }
}

export const cadastroRoute: Routes = [
    {
        path: 'cadastro',
        component: CadastroComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cadastros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cadastro/:id/view',
        component: CadastroDetailComponent,
        resolve: {
            cadastro: CadastroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cadastros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cadastro/new',
        component: CadastroUpdateComponent,
        resolve: {
            cadastro: CadastroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cadastros'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cadastro/:id/edit',
        component: CadastroUpdateComponent,
        resolve: {
            cadastro: CadastroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cadastros'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cadastroPopupRoute: Routes = [
    {
        path: 'cadastro/:id/delete',
        component: CadastroDeletePopupComponent,
        resolve: {
            cadastro: CadastroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cadastros'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
