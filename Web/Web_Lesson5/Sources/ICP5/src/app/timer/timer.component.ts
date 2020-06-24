import { Component, OnInit } from '@angular/core';
import { interval } from 'rxjs';
import { takeWhile } from 'rxjs/operators';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss']
})
export class TimerComponent implements OnInit {
  target: any;
  days: number;
  hours: number;
  minutes: number;
  seconds: number;
  stopped: boolean;
  started: boolean;

  constructor() {
  }

  ngOnInit(): void {
    document.body.style.backgroundAttachment = 'fixed';
    document.body.style.backgroundImage = 'linear-gradient(dodgerblue, darkblue)';
    this.stopped = true;
    this.started = false;
  }

  setTime() {
    this.stopped = false;
    this.started = true;
    this.countTime();
    interval(1000)
      .pipe(takeWhile(() => this.stopped === false))
      .subscribe(() => { this.countTime(); });
  }

  countTime() {
    let time = new Date(this.target).getTime() - new Date().getTime();
    if (time < 0) {
      this.stopped = true;
    } else {
      this.days = Math.floor( time / 1000 / 60 / 60 / 24);
      time -= this.days * 1000 * 60 * 60 * 24;
      this.hours = Math.floor( time / 1000 / 60 / 60);
      time -= this.hours * 1000 * 60 * 60;
      this.minutes = Math.floor( time / 1000 / 60);
      time -= this.minutes * 1000 * 60;
      this.seconds = Math.floor( time / 1000);
    }
  }

  getTime(): string {
    return formatDate(this.target, 'longDate', 'en-US');
  }
}
