using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class beaconController : BaseApiController
    {
        // GET api/beacon
        public IEnumerable<beacon> Getbeacons()
        {
            return db.beacon.AsEnumerable();
        }

        // GET api/beacon/5
        public beacon Getbeacon(string id)
        {
            beacon beacon = db.beacon.Find(id);
            if (beacon == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return beacon;
        }

        // PUT api/beacon/5
        public HttpResponseMessage Putbeacon(string id, beacon beacon)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != beacon.beaconid)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(beacon).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/beacon
        public HttpResponseMessage Postbeacon(beacon beacon)
        {
            if (ModelState.IsValid)
            {
                db.beacon.Add(beacon);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, beacon);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = beacon.beaconid }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/beacon/5
        public HttpResponseMessage Deletebeacon(string id)
        {
            beacon beacon = db.beacon.Find(id);
            if (beacon == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.beacon.Remove(beacon);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, beacon);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}