/***
 *@file simpson.c
 *@brief calculates the area under a curve using Simpson's rule.
 *@author Bryonna Klumker
 *@date 10-25-15
 *@bug
 */
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/*
 * The integrate part of the program. Uses Simpson's Rule (trapezoids).
 * @param *fnc the pointer to the mathematical expression we're using
 * @param a bound a on interval [a,b]
 * @param b bound b on interval [a,b]
 * @param n the number of trapezoids
 */
 double integrate(double(*fnc)(double x), double a, double b, int n)
{
	double h1;
	double h2;
	double width;
	double area = 0.0;
	double x;
	int i = 1;
	
	width = (b - a) / n;
	x = a;
	for(; i <= n; i++) {
		h1 = (*fnc)(x);
		x += width;
		h2 = (*fnc)(x);
		area += ((h1 + h2) / 2) * width;
	}
 return area;
}

double squart(double x)
{
	return sqrt(x);
}
int main (void)
{
	double(*fnc)();
	double area;
	int n = 1000;

	fnc = &log;
	area = integrate(fnc, 0.5, 2.0, n);
	printf("integral of ln(x) from 0.5 to 2 is %lf\n....\n", area);

	fnc = &exp;
	area = integrate(fnc, 0.0, 1.0, n);
	printf("integral of e^(x) from 0 to 1 is %lf\n....\n", area);
	
	fnc = &cos;
	area = integrate(fnc, 0.0, M_PI, n);
	printf("integral of cos(x) from 0 to pi is %lf\n....\n", area);

	fnc = &sin;
	area = integrate(fnc, 0.0, M_PI, n);
	printf("integral of sin(x) from 0 to pi is %lf\n....\n", area);

	fnc = &sqrt;
	area = integrate(fnc, 0.0, 10.0, n);
	printf("integral of sqrt(x) from 0 to 10 is %lf\n", area);
	
	return 0;
}
