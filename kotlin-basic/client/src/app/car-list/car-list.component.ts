import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CarService, GiphyService } from '../shared';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CarListComponent implements OnInit {
  cars: Array<any>;

  constructor(private carService: CarService, private giphyService: GiphyService) { }

  ngOnInit() {
    this.carService.getAll().subscribe(data => {
      this.cars = data;
      for (const car of this.cars) {
        this.giphyService.get(car.name).subscribe(url => car.giphyUrl = url);
      }
    }, (error) => console.error(error));
  }

}
