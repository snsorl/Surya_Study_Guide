import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currencyFormat',
  pure: true // Ensures the pipe only re-evaluates when inputs change
})
export class CurrencyFormatPipe implements PipeTransform {

  // TODO: Complete the transform signature and implementation
  transform(
    value: number, 
    symbol: string = '$', 
    position: 'before' | 'after' = 'before'
  ): string {
    if (value === null || value === undefined || isNaN(value)) {
      return '';
    }

    // TODO: Write formatting logic returning symbol and value in correct order
    const formattedValue = value.toFixed(2);
    
    if (position === 'before') {
      return `${symbol}${formattedValue}`;
    } else {
      return `${formattedValue}${symbol}`;
    }
  }
}
