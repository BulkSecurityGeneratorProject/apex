import { NgModule } from '@angular/core';

import { ApexSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ApexSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ApexSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ApexSharedCommonModule {}
