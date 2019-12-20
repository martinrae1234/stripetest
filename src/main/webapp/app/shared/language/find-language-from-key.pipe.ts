import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'findLanguageFromKey' })
export class FindLanguageFromKeyPipe implements PipeTransform {
  private languages: any = {
    en: { name: 'English' },
    es: { name: 'Español' }
    // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
  };
  transform(lang: string): string {
    console.log('I am in language transform'); // REMOVE ME
    return this.languages[lang].name;
  }
}
