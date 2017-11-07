import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { BeerService, GiphyService } from '../shared';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-beer-edit',
  templateUrl: './beer-edit.component.html',
  styleUrls: ['./beer-edit.component.css']
})
export class BeerEditComponent implements OnInit {
  beer: any = {};

  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private beerService: BeerService,
              private giphyService: GiphyService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.beerService.get(id).subscribe((beer: any) => {
          if (beer) {
            this.beer = beer;
            this.beer.href = beer._links.self.href;
            this.giphyService.get(beer.name).subscribe(url => beer.giphyUrl = url);

          } else {
            console.log(`Beer with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/beer-list']);
  }

  save(form: NgForm) {
    this.beerService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }

  remove(href) {
    this.beerService.remove(href).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }
}
