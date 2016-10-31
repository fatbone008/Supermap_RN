/**
 * Created by will on 2016/10/17.
 */
test('adds 1 + 2 to equal 3', () => {
    const sum = require('./sum');
    expect(sum(1, 2)).toBe(3);
});