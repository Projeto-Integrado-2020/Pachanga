import { Injectable } from '@angular/core';
import { LogFields } from './log-data.interface';
import { Logger } from './logger';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  private logger: Logger;
  private userId: string;

  constructor() {}

  public setUserId(userId: string) {
    this.userId = userId;
  }

  public initialize() {
    this.logger = new Logger(environment.appName, environment.endpoints.log);
  }

  public logHttpInfo(info: any, elapsedTime: number, requestPath: string) {
    // TODO: create and set correlation id
    const url = location.href;
    const logFields: LogFields = {
      environment: environment.env,
      userId: this.userId,
      requestPath,
      elapsedTime,
      url,
    };

    this.logger.log('Information', `${info}`, logFields);
  }

  public logWarning(errorMsg: string) {
    const url = location.href;

    const logFields: LogFields = {
      environment: environment.env,
      userId: this.userId,
      requestPath: '',
      elapsedTime: 0,
      url,
    };

    this.logger.log('Warning', errorMsg, logFields);
  }

  public logError(errorMsg: string) {
    const url = location.href;

    const logFields: LogFields = {
      environment: environment.env,
      userId: this.userId,
      requestPath: '',
      elapsedTime: 0,
      url,
    };

    this.logger.log('Error', errorMsg, logFields);
  }

  public logInfo(info: any) {
    const url = location.href;

    const logFields: LogFields = {
      environment: environment.env,
      userId: this.userId,
      requestPath: '',
      elapsedTime: 0,
      url,
    };

    this.logger.log('Information', info, logFields);
  }
}
